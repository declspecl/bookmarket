import { NavBar } from "@/components/NavBar";
import { ListingWithSeller } from "@/lib/api/model";
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { FilterDirectionDropdown, FilterTypeDropdown } from "@/components/shop/FilterDropdowns";
import { FilterDirection, FilterType } from "@/lib/shop/model";
import { useMemo, useState } from "react";
import Fuse from "fuse.js";
import { Link } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { getAllListings, getImageForListingById } from "@/lib/api/apis";
import { LucideLoader2 } from "lucide-react";

function ListingCard({ listing }: { listing: ListingWithSeller }) {
    const { isLoading, error, data: imageData } = useQuery({
        queryKey: ["GetImageForListingById", { listingId: listing.id }],
        queryFn: () => getImageForListingById({ listingId: listing.id })
    });

    const listingDate = new Date(listing.createdAt);

    const preview = useMemo(() => (
        <>
            {isLoading ? (
                <div className="w-full h-full rounded-tl-lg rounded-tr-lg bg-gray-400 animate-pulse" />
            ) : error ? (
                <p>Failed to load image</p>
            ) : (imageData && imageData.image && imageData.image.rawBytes) ? (
                <img
                    loading="lazy"
                    src={`data:image/png;base64,${imageData!.image.rawBytes}`}
                    alt="Listing image (failed to load)"
                    className="w-full h-full object-contain rounded-tr-lg rounded-tl-lg"
                />
            ) : (
                <div className="w-full h-full rounded-tl-lg rounded-tr-lg bg-gray-500" />
            )}
        </>
    ), [isLoading, error, imageData]);

    return (
        <Card className="flex flex-col">
            <div className="w-full h-40 bg-gray-300 rounded-tl-lg rounded-tr-lg">
                {preview}
            </div>

            <CardContent className="grow flex flex-col gap-2 justify-between py-4 px-6">
                <div className="flex flex-col gap-2">
                    <p className="text-xl font-medium text-wrap">{listing.title} ({listing.classSubject})</p>
                    <p className="text-sm text-muted-foreground">Listed {listingDate.toLocaleString()}</p>
                </div>

                <div className="flex flex-col justify-between gap-2 sm:w-full sm:flex-row sm:items-center-center">
                    <p className="flex flex-row items-center gap-2">
                        <span className="text-lg font-bold">${listing.price}</span>
                        <span className="text-sm text-muted-foreground">Condition: {listing.condition}</span>
                    </p>

                    <Button variant="default" size="lg" asChild>
                        <Link to={`/listing/${listing.id}`} className="text-lg font-medium">View Details</Link>
                    </Button>
                </div>
            </CardContent>
        </Card>
    );
}

export function ShopPage() {
    const [searchInput, setSearchInput] = useState<string>("");
    const [filterType, setFilterType] = useState<FilterType | null>(null);
    const [filterDirection, setFilterDirection] = useState<FilterDirection | null>(null);

    const { isLoading, error,  data } = useQuery({
        queryKey: ["GetAllListings"],
        queryFn: getAllListings
    });

    const listings = data?.listings;

    const filteredListings = useMemo(() => {
        if (!listings) {
            return [];
        }

        if (filterType === null || filterDirection === null) {
            return listings;
        }

        switch (filterType) {
            case FilterType.LIST_DATE:
                return listings.sort((a, b) => {
                    if (filterDirection === FilterDirection.ASC) {
                        return new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime();
                    }
                    else {
                        return new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime();
                    }
                });

            case FilterType.TITLE_ALPHABETICAL:
                return listings.sort((a, b) => {
                    if (filterDirection === FilterDirection.ASC) {
                        return a.title.localeCompare(b.title);
                    }
                    else {
                        return b.title.localeCompare(a.title);
                    }
                });

            case FilterType.PRICE:
                return listings.sort((a, b) => {
                    if (filterDirection === FilterDirection.ASC) {
                        return a.price - b.price;
                    }
                    else {
                        return b.price - a.price;
                    }
                });

            case FilterType.CONDITION:
                return listings.sort((a, b) => {
                    if (filterDirection === FilterDirection.ASC) {
                        return a.condition.localeCompare(b.condition);
                    }
                    else {
                        return b.condition.localeCompare(a.condition);
                    }
                });

            case FilterType.CLASS_SUBJECT_ALPHABETICAL:
                return listings.sort((a, b) => {
                    if (filterDirection === FilterDirection.ASC) {
                        return a.classSubject.localeCompare(b.classSubject);
                    }
                    else {
                        return b.classSubject.localeCompare(a.classSubject);
                    }
                });
        }
    }, [listings, filterType, filterDirection]);

    const fuse = new Fuse(filteredListings, {
        keys: ["title", "description", "authorName", "condition", "classSubject"],
        threshold: 0.65
    });

    // https://www.fusejs.io/demo.html
    const searchOrderedListings = useMemo(() => {
        if (!searchInput) {
            return filteredListings;
        }

        return fuse.search(searchInput).map(result => result.item);
    }, [filteredListings, searchInput]);

    return (
        <div>
            <NavBar />

            <div className="p-8">
                <h1 className="text-secondary font-bold text-6xl mb-4">Shop</h1>

                <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between sm:gap-8">
                    <Input placeholder="Search for a listing..." className="w-full grow" value={searchInput} onChange={(e) => setSearchInput(e.target.value)} />

                    <div className="flex flex-col gap-4 sm:flex-row">
                        <FilterTypeDropdown filterType={filterType} setFilterType={setFilterType} />
                        <FilterDirectionDropdown filterDirection={filterDirection} setFilterDirection={setFilterDirection} />
                    </div>
                </div>

                <div className="my-6" />

                {isLoading ? (
                    <LucideLoader2 className="animate-spin w-12 h-12" />
                ) : error ? (
                    <p>We encountered a fatal error when trying to read the listings. Please try again later.</p>
                ) : (
                    <>
                        {searchOrderedListings.length === 0 ? (
                            <p>There are currently no listings! Want to <Link to="/create-listing" className="underline">create one</Link>?</p>
                        ) : (
                            <div className="flex flex-col gap-4 lg:grid lg:grid-cols-2 lg:gap-8 xl:grid-cols-3 ">
                                {searchOrderedListings.map((listing) => (
                                    <ListingCard key={`listing-${listing.id}`} listing={listing} />
                                ))}
                            </div>
                        )}
                    </>
                )}
            </div>
        </div>
    );
}
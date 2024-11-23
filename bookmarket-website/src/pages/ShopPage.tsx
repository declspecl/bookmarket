import { NavBar } from "@/components/NavBar";
import { mockGetAllListingsResponse } from "@/lib/api/mocks";
import { Listing } from "@/lib/api/model";
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { FilterDirectionDropdown, FilterTypeDropdown } from "@/components/shop/FilterDropdowns";
import { FilterDirection, FilterType } from "@/lib/shop/model";
import { useMemo, useState } from "react";
import Fuse from "fuse.js";
import { Link } from "react-router-dom";

function ListingCard({ listing }: { listing: Listing }) {
    const listingDate = new Date(listing.createdAt);

    return (
        <Card>
            <div className="bg-gray-400 w-full min-h-40 rounded-tr-lg rounded-tl-lg" />

            <CardContent className="flex flex-row items-center justify-between py-4 px-6">
                <div className="flex flex-col gap-2">
                    <p className="text-xl font-medium">{listing.title} ({listing.classSubject})</p>

                    <p className="text-sm text-muted-foreground">Listed {listingDate.toLocaleString()}</p>

                    <p className="flex flex-row items-center gap-2">
                        <span className="text-lg font-bold">${listing.price}</span>
                        <span className="text-sm text-muted-foreground">Condition: {listing.condition}</span>
                    </p>
                </div>

                <Button variant="default" asChild>
                    <Link to={`/listing/${listing.id}`}>View Details</Link>
                </Button>
            </CardContent>
        </Card>
    );
}

export function ShopPage() {
    const [searchInput, setSearchInput] = useState<string>("");
    const [filterType, setFilterType] = useState<FilterType | null>(null);
    const [filterDirection, setFilterDirection] = useState<FilterDirection | null>(null);

    const listings = mockGetAllListingsResponse;

    const filteredListings = useMemo(() => {
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
                <div className="flex flex-row items-center justify-between gap-8">
                    <Input placeholder="Search for a listing..." className="w-0 grow" value={searchInput} onChange={(e) => setSearchInput(e.target.value)} />

                    <div className="flex flex-row gap-4">
                        <FilterTypeDropdown filterType={filterType} setFilterType={setFilterType} />
                        <FilterDirectionDropdown filterDirection={filterDirection} setFilterDirection={setFilterDirection} />
                    </div>
                </div>

                <div className="my-6" />

                <div className="flex flex-col gap-4 lg:grid lg:grid-cols-3 lg:gap-8">
                    {searchOrderedListings.map((listing) => (
                        <ListingCard key={listing.id} listing={listing} />
                    ))}
                </div>
            </div>
        </div>
    );
}
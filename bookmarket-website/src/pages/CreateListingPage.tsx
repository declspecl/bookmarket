import "./CreateListingPage.css";

import { NavBar } from "@/components/NavBar";
import { Select, SelectContent, SelectItem, SelectTrigger } from "@/components/ui/select";
import { Availability, ClassSubject, Condition } from "@/lib/api/model";
import { ChangeEvent, useState } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { createListing } from "@/lib/api/apis";
import { Textarea } from "@/components/ui/textarea";
import { useNavigate } from "react-router-dom";
import { LucideLoader2 } from "lucide-react";
import { useQueryClient } from "@tanstack/react-query";

interface ImageUpload {
    image: File | undefined;
    previewUrl: string | undefined;
    rawBytes: Uint8Array | undefined;
}

interface ImageUploaderProps {
    imageUpload: ImageUpload;
    setImageUpload: (imageUpload: ImageUpload) => void;
}

function ImageUploader({ imageUpload, setImageUpload }: ImageUploaderProps) {
    const handleImageInput = async (e: ChangeEvent<HTMLInputElement>) => {
        const file: File | undefined = e.target.files?.[0];
        if (!file) return;

        const previewUrl = URL.createObjectURL(file);

        const arrayBuffer = await file.arrayBuffer();
        const rawBytes = new Uint8Array(arrayBuffer);

        setImageUpload({
            image: file,
            previewUrl: previewUrl,
            rawBytes: rawBytes
        });
    };

    return (
        <div>
            <Input
                type="file"
                accept="image/png"
                onChange={handleImageInput}
            />

            {imageUpload.previewUrl && (
                <img src={imageUpload.previewUrl} className="max-w-96 aspect-auto" alt="Image previewUrl" />
            )}
        </div>
    )
}

export function CreateListingPage() {
    const [inputtedTitle, setInputtedTitle] = useState("");
    const [inputtedDescription, setInputtedDescription] = useState("");
    const [inputtedAuthor, setInputtedAuthor] = useState("");
    const [inputtedImage, setInputtedImage] = useState<ImageUpload>({ image: undefined, previewUrl: undefined, rawBytes: undefined });
    const [selectedCondition, setSelectedCondition] = useState<Condition | undefined>(undefined);
    const [selectedSubject, setSelectedSubject] = useState<ClassSubject | undefined>(undefined);
    const [inputtedPrice, setInputtedPrice] = useState("");

    const queryClient = useQueryClient();

    const navigate = useNavigate();

    const [isCreatingListing, setIsCreatingListing] = useState(false);

    return (
        <div>
            <NavBar />

            <div className="create-listing-page">
                <h1 className="page-title">Create Listing</h1>

                <div className="flex flex-col gap-6">
                    <div>
                        <h2 className="title">Book Title</h2>
                        <label htmlFor="book" />
                        <Input
                            type="text"
                            id="title"
                            name="title"
                            placeholder="Enter book title"
                            value={inputtedTitle}
                            onChange={(e) => setInputtedTitle(e.target.value)}
                            required
                        />
                    </div>

                    <div>
                        <h2 className="title">Listing description</h2>
                        <label htmlFor="description" />
                        <Textarea
                            id="description"
                            name="descrition"
                            placeholder="Enter listing description"
                            value={inputtedDescription}
                            onChange={(e) => setInputtedDescription(e.target.value)}
                            required
                        />
                    </div>

                    <div>
                        <h2 className="title">Book Primary Author</h2>
                        <label htmlFor="author" />
                        <Input
                            type="text"
                            id="author"
                            name="author"
                            placeholder="Enter author name"
                            value={inputtedAuthor}
                            onChange={(e) => setInputtedAuthor(e.target.value)}
                            required
                        />
                    </div>

                    <div>
                        <h2 className="title">Book Condition</h2>
                        <label htmlFor="condition" />

                        <Select value={selectedCondition} onValueChange={setSelectedCondition as (_: Condition) => void}>
                            <SelectTrigger>{selectedCondition || "Select a condition..."}</SelectTrigger>

                            <SelectContent className="form-group">
                                {Object.values(Condition).map((conditionOption) => (
                                    <SelectItem key={conditionOption} value={conditionOption}>{conditionOption}</SelectItem>
                                ))}
                            </SelectContent>
                        </Select>
                    </div>

                    <div>
                        <h2 className="title">Upload Image</h2>
                        <label htmlFor="image" />
                        <ImageUploader imageUpload={inputtedImage} setImageUpload={setInputtedImage} />
                    </div>

                    <div>
                        <h2 className="title">Class Subject</h2>
                        <label htmlFor="subject" />

                        <Select value={selectedSubject} onValueChange={setSelectedSubject as (_: ClassSubject) => void}>
                            <SelectTrigger>{selectedSubject || "Select a subject..."}</SelectTrigger>

                            <SelectContent className="form-group">
                                {Object.values(ClassSubject).map((subjectOption) => (
                                    <SelectItem key={subjectOption} value={subjectOption}>{subjectOption}</SelectItem>
                                ))}
                            </SelectContent>
                        </Select>
                    </div>

                    <div>
                        <h2 className="title">List Price (USD)</h2>
                        <label htmlFor="price" />
                        <Input
                            type="number"
                            id="price"
                            name="price"
                            min="0.01"
                            max="999.99"
                            step="0.01"
                            placeholder="0.00"
                            value={inputtedPrice}
                            onChange={(e) => setInputtedPrice(e.target.value)}
                            required
                        />
                    </div>

                    <div className="submit-box">
                        <Button
                            onClick={async () => {
                                if (!inputtedTitle || !inputtedAuthor || !selectedCondition || !inputtedImage.rawBytes || !selectedSubject || !inputtedPrice) {
                                    alert("Please fill out all fields!");
                                    return;
                                }

                                if (!inputtedPrice.match(/^\d+(\.\d{1,2})?$/)) {
                                    alert("Please enter a valid price!");
                                    return;
                                }

                                setIsCreatingListing(true);

                                let response;
                                try {
                                    response = await createListing({
                                        title: inputtedTitle,
                                        description: inputtedDescription,
                                        authorName: inputtedAuthor,
                                        price: parseFloat(inputtedPrice),
                                        condition: selectedCondition,
                                        availability: Availability.AVAILABLE,
                                        classSubject: selectedSubject,
                                        imageRawBytes: btoa(String.fromCharCode(...inputtedImage.rawBytes))
                                    });
                                }
                                catch (e) {
                                    alert("Failed to create listing! Please try again later.");
                                    setIsCreatingListing(false);
                                    return;
                                }

                                setIsCreatingListing(false);

                                if (!response || !response.listing) {
                                    alert("Failed to create listing! Please try again later.");
                                    return;
                                }

                                queryClient.invalidateQueries({
                                    queryKey: ["GetAllListings"]
                                });
                                
                                navigate(`/listing/${response.listing.id}`);
                            }}
                        >
                            {isCreatingListing ? (
                                <LucideLoader2 className="animate-spin w-16 h-16" />
                            ) : "Create Listing"}
                        </Button>
                    </div>
                </div>
            </div>   
        </div>
    );
}

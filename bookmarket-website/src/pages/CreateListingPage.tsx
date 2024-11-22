import { NavBar } from "@/components/NavBar";
import React from 'react';
import "./CreateListingPage.css";
import { Select, SelectContent, SelectItem, SelectTrigger } from "@/components/ui/select";
import { Button } from "@/components/ui/button";

export function CreateListingPage() {

    return (
        <div className="create-listing-page">
            <NavBar />

            <h1 className="page-title">Create Listing</h1>

            <h2 className="title">Book Title</h2>
                <div className="book">
                    <label htmlFor="book"></label>
                    <input
                        type="text"
                        id="title"
                        name="title"
                        placeholder="Enter book title"
                        required
                    />
                </div>

                <h2 className="title">Book Primary Author</h2>
                <div className="author">
                    <label htmlFor="author"></label>
                    <input
                        type="text"
                        id="author"
                        name="author"
                        placeholder="Enter author name"
                        required
                    />
                </div>

                <h2 className="title">Book Condition</h2>
                <div className="condition">
                    <label htmlFor="condition"></label>
                    <Select>
                        <SelectTrigger>
                            <Button>Select a condition...</Button>
                        </SelectTrigger>
                        <SelectContent className="form-group">
                            <SelectItem value="NEW">New</SelectItem>
                            <SelectItem value="LIKE_NEW">Like new</SelectItem>
                            <SelectItem value="GOOD">Good</SelectItem>
                            <SelectItem value="OKAY">Okay</SelectItem>
                            <SelectItem value="BAD">Bad</SelectItem>
                        </SelectContent>
                    </Select>
                </div>

                <h2 className="title">Upload Image</h2>
                <div className="image">
                    <label htmlFor="image"></label>
                    <input 
                        type="text"
                        id="image"
                        name="image"
                        />
                </div>

                <h2 className="title">Class Subject</h2>
                <div className="subject">
                    <label htmlFor="subject"></label>
                    <Select>
                        <SelectTrigger>
                            <Button>Select a subject</Button>
                            </SelectTrigger>
                                <SelectContent className="form-group">
                                    <SelectItem value="MTH">MTH</SelectItem>
                                    <SelectItem value="CSI">CSI</SelectItem>
                                    <SelectItem value="ENG">ENG</SelectItem>
                                    <SelectItem value="PHL">PHL</SelectItem>
                                    <SelectItem value="SCI">SCI</SelectItem>
                                </SelectContent>
                        </Select>
                </div>

                <h2 className="title">List Price (USD)</h2>
                <div className="price">
                    <label htmlFor="price"></label>
                    <input
                        type="number"
                        id="price"
                        name="price"
                        placeholder="Enter price"
                        required
                        />
                </div>

                <div className="submit-box">
                    <button className="submit-button">Create Listing</button>
                </div>

        </div>   
    );
}

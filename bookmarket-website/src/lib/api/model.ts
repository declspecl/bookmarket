export enum Condition {
    NEW = "New",
    LIKE_NEW = "Like New",
    GOOD = "Good",
    OKAY = "Okay",
    BAD = "Bad"
}

export enum Availability {
    AVAILABLE = "Available",
    CANCELLED = "Cancelled",
    SOLD = "Sold"
}

export enum ClassSubject {
    MTH = "MTH",
    ENG = "ENG",
    HC = "HC",
    CSI = "CSI",
    BIO = "BIO",
    CHM = "CHM",
    PHY = "PHY"
}

export interface User {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
}

export interface Listing {
    id: number;
    title: string;
    description: string;
    authorName: string;
    price: number;
    condition: Condition;
    availability: Availability;
    classSubject: ClassSubject;
    sellerId: number;
    createdAt: string;
}

export interface ListingWithSeller {
    id: number;
    title: string;
    description: string;
    authorName: string;
    price: number;
    condition: Condition;
    availability: Availability;
    classSubject: ClassSubject;
    seller: User;
    createdAt: string;
}

export interface Comment {
    id: number;
    content: string;
    createdAt: string;
    creatorId: number;
    parentListingId: number;
    parentCommentId: number | undefined;
}

export interface CommentWithCreator {
    id: number;
    content: string;
    createdAt: string;
    creator: User;
    parentListingId: number;
    parentCommentId: number | undefined;
}

export interface Image {
    id: number;
    listingId: number;
    rawBytes?: string;
}

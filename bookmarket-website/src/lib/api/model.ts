export interface Listing {
    id: number;
    title: string;
    description: string;
    authorName: string;
    price: number;
    condition: string;
    availability: string;
    classSubject: string;
    sellerId: string;
}

export interface Comment {
    id: string;
    content: string;
    createdAt: string;
    creatorId: number;
    parentListingId: number;
    parentCommentId: number;
}

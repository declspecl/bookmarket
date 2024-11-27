import { Comment, CommentWithCreator, Listing, ListingWithSeller } from "./model";

// ================
// = LISTING APIS =
// ================

interface CreateListingRequest {
    title: string;
    description: string;
    authorName: string;
    price: number;
    condition: string;
    availability: string;
    classSubject: string;
}

interface CreateListingResponse {
    listing: Listing;
}

export async function createListing(request: CreateListingRequest): Promise<CreateListingResponse> {
    const response = await fetch("/api/listings", {
        method: "POST",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request)
    });

    const jsonResponse = await response.json();
    return jsonResponse;
}

interface GetAllListingsResponse {
    listings: ListingWithSeller[];
}

export async function getAllListings(): Promise<GetAllListingsResponse> {
    const response = await fetch("/api/listings", {
        method: "GET",
        credentials: "include"
    });

    const jsonResponse = await response.json();
    return jsonResponse;
}

interface GetListingRequest {
    listingId: number;
}

interface GetListingResponse {
    listing: ListingWithSeller;
}

export async function getListing(request: GetListingRequest): Promise<GetListingResponse> {
    const response = await fetch(`/api/listings/${request.listingId}`, {
        method: "GET",
        credentials: "include"
    });

    const jsonResponse = await response.json();
    return jsonResponse;
}

// ================
// = COMMENT APIS =
// ================

interface CreateCommentRequest {
    content: string;
    listingId: number;
    // this says that the `parentCommentId` can optionally be included in the request (for if a comment is a reply)
    parentCommentId: number | undefined;
}

interface CreateCommentResponse {
    comment: CommentWithCreator;
}

export async function createComment(request: CreateCommentRequest): Promise<CreateCommentResponse> {
    const url = request.parentCommentId
        ? `/api/listings/${request.listingId}/comments/${request.parentCommentId}/reply`
        : `/api/listings/${request.listingId}/comments`;

    const response = await fetch(url, {
        method: "POST",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            content: request.content
        }),
    });
    const jsonResponse = await response.json();
    return jsonResponse;
}

interface GetAllCommentsRequest {
    listingId: number;
}

interface GetAllCommentsResponse {
    comments: CommentWithCreator[];
}

export async function getAllComments(request: GetAllCommentsRequest): Promise<GetAllCommentsResponse> {
    const response = await fetch(`/api/listings/${request.listingId}/comments`, {
        method: "GET",
        credentials: "include",
    });
    const jsonResponse = await response.json();
    return jsonResponse;
}

// =============
// = AUTH APIS =
// =============

interface SignupRequest {
    email : string;
    firstName : string;
    lastName : string;
    password : string;
}

export async function signup(request: SignupRequest): Promise<Response> {
    const response = await fetch("/api/auth/signup", {
        method: "POST",
        credentials: "include",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(request),
    });

    return response;
}

interface LoginRequest {
    email : string;
    password : string;
}

export async function login(request: LoginRequest): Promise<Response> {
    const response = await fetch("/api/auth/login", {
        method: "POST",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
    });

    return response;
}

export async function logout(): Promise<Response> {
    const response = await fetch("/api/auth/logout", {
        method: "POST",
        credentials: "include",
    });

    return response;
}

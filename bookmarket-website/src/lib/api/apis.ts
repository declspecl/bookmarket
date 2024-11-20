import { Listing } from "./model";

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
    listings: Listing[];
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
    listing: Listing;
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
    // TODO
    // this says that the `parentCommentId` can optionally be included in the request (for if a comment is a reply)
    parentCommentId: number | undefined;
}

interface CreateCommentResponse {
    comment: Comment;
}

export async function createComment(request: CreateCommentRequest): Promise<CreateCommentResponse> {
    // TODO
}

interface GetAllCommentsRequest {
    // TODO
}

interface GetAllCommentsResponse {
    comments: Comment[];
}

export async function getAllComments(request: GetAllCommentsRequest): Promise<GetAllCommentsResponse> {
    // TODO
}

// =============
// = AUTH APIS =
// =============

interface SignupRequest {
    // TODO
}

export async function signup(request: SignupRequest): Promise<void> {
    // TODO
}

interface LoginRequest {
    // TODO
}

export async function login(request: LoginRequest): Promise<void> {
    // TODO
}

interface LogoutRequest {
    // TODO
}

export async function logout(request: LogoutRequest): Promise<void> {
    // TODO
}

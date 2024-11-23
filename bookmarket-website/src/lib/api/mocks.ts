import { Listing, Condition, Availability, ClassSubject, ListingWithSeller, CommentWithCreator } from "./model";

export const mockGetAllListingsResponse: Listing[] = [
    {
        id: 1,
        title: "Some listing title",
        description: "Some listing description",
        authorName: "Some author name",
        price: 24.99,
        condition: Condition.GOOD,
        availability: Availability.AVAILABLE,
        classSubject: ClassSubject.MTH,
        sellerId: 10,
        createdAt: "2021-09-01T00:00:00Z"
    },
    {
        id: 2,
        title: "Some listing title 2",
        description: "Some listing description 2",
        authorName: "Some author name 2",
        price: 0.01,
        condition: Condition.BAD,
        availability: Availability.AVAILABLE,
        classSubject: ClassSubject.ENG,
        sellerId: 11,
        createdAt: "2021-09-02T00:00:00Z"
    },
    {
        id: 3,
        title: "Some listing title 3",
        description: "Some listing description 3",
        authorName: "Some author name 3",
        price: 24.99,
        condition: Condition.GOOD,
        availability: Availability.AVAILABLE,
        classSubject: ClassSubject.HC,
        sellerId: 12,
        createdAt: "2021-09-03T00:00:00Z"
    },
    {
        id: 4,
        title: "Some listing title 4",
        description: "Some listing description 4",
        authorName: "Some author name 4",
        price: 100,
        condition: Condition.OKAY,
        availability: Availability.AVAILABLE,
        classSubject: ClassSubject.MTH,
        sellerId: 13,
        createdAt: "2021-09-04T00:00:00Z"
    },
    {
        id: 5,
        title: "Some listing title 5",
        description: "Some listing description 5",
        authorName: "Some author name 5",
        price: 10.24,
        condition: Condition.LIKE_NEW,
        availability: Availability.AVAILABLE,
        classSubject: ClassSubject.MTH,
        sellerId: 14,
        createdAt: "2021-09-05T00:00:00Z"
    },
    {
        id: 6,
        title: "Some listing title 6",
        description: "Some listing description 6",
        authorName: "Some author name 6",
        price: 24.99,
        condition: Condition.BAD,
        availability: Availability.AVAILABLE,
        classSubject: ClassSubject.CSI,
        sellerId: 15,
        createdAt: "2021-09-06T00:00:00Z"
    }
]

export const mockGetListingByIdResponse: ListingWithSeller = {
    id: 1,
    title: "Some listing title",
    description: "Some listing description",
    authorName: "Some author name",
    price: 24.99,
    condition: Condition.GOOD,
    availability: Availability.AVAILABLE,
    classSubject: ClassSubject.MTH,
    seller: {
        id: 10,
        firstName: "Some",
        lastName: "Seller",
        email: "someseller@oakland.edu"
    },
    createdAt: "2021-09-01T00:00:00Z"
}

export const mockGetAllCommentsForListingResponse: CommentWithCreator[] = [
    {
        id: "4",
        content: "Some comment content 4",
        parentListingId: 1,
        parentCommentId: undefined,
        createdAt: "2021-09-04T00:00:00Z",
        creator: {
            id: 13,
            firstName: "Bobby",
            lastName: "Joe Jr. III",
            email: "qwenokqwneokwqoe@oakland.edu"
        }
    },
    {
        id: "3",
        content: "Some comment content 3",
        parentListingId: 1,
        parentCommentId: 2,
        createdAt: "2021-09-03T00:00:00Z",
        creator: {
            id: 12,
            firstName: "John",
            lastName: "Doe",
            email: "ANOTHERANOTHERcommenter@oakland.edu"
        }
    },
    {
        id: "2",
        content: "Some comment content 2",
        parentListingId: 1,
        parentCommentId: 1,
        createdAt: "2021-09-02T00:00:00Z",
        creator: {
            id: 11,
            firstName: "Jane",
            lastName: "Doe",
            email: "ANOTHERcommenter@oakland.edu"
        }
    },
    {
        id: "1",
        content: "Some comment content",
        parentListingId: 1,
        parentCommentId: undefined,
        createdAt: "2021-09-01T00:00:00Z",
        creator: {
            id: 10,
            firstName: "Bill",
            lastName: "McGee",
            email: "somecommenter@oakland.edu"
        }
    }
]

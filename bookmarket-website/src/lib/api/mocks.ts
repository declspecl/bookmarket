import { Listing, Condition, Availability, ClassSubject } from "./model";

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
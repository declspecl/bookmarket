# Comments APIs

## Which use cases do the comments APIs need to satisfy?

- Get all comments for a listing
- Create a comment on a listing
- Reply to a comment on a listing

## Required APIs and details

### Get all comments for a listing

On the frontend, we want to display a few bits of information for comments:
- The name of the comment's author/creator
- The comment's content of course
- The date and time it was created

And it's usually good practice to also return the ID of things, so in total, that makes these fields required on each comment returned by this API:
- `commentId`
- `authorName`
- `content`
- `createdAt`

Since we want to GET all the comments for a listing, I think the best HTTP verb for this is `GET`.
Also, since comments are going to be scoped to each listing, the frontend will need to send to the backend the ID of the listing it wants the comments for.
This could be supplied as an HTTP parameter, but I think a better, more idiomatic way to do this is to include the parameter in the endpoint URL.

So all in all, the REST endpoint for this will be `GET /api/listing/{listingId}/comment` and it will return comment objects with the above fields.
For example, to get all comments for the listing with the ID 102, the request will be `GET /api/listing/102/comment`.

### Create a comment on a listing

As mentioned above, all comments need to have these fields:
- `commentId`
- `authorName`
- `content`
- `createdAt`

The `commentId` will be generated by the database after we insert it.
The `authorName` can be found by taking the session cookie and finding the user that it belongs to.
The `content` will need to be provided in the request since this is dynamic.
The `createdAt` can be generated by the Java server by just doing `new Date()`.

But the database also needs to know what listing the comment belongs to.
This could be supplied as an HTTP parameter, but I think a better, more idiomatic way to do this is to include the parameter in the endpoint URL.
So, the only field we need manually supplied to the API is `content`.

I think the best HTTP verb for this is `POST` since you "post" comments.
So, I think the best REST endpoint for this is `POST /api/listing/{listingId}/comment` (same as `GET` all listings, but this time it's `POST`).
And it will require a request body in JSON like so:
```
{
    "content": "..."
}
```

## Reply to a comment on a listing

To do this, I'm adding a `parentCommentId` field to all comments that can either be null if the comment was made on the listing itself or the ID of the comment that it is replying to.
This also lets users reply to replies, since every reply is just a normal comment that has it's own comment ID; the only difference is that it also has a `parentCommentId` field.
This is useful especially for the frontend so that when we see `GET` all listings and see a comment object with a `parentCommentId`, we'll know it's a reply and to put it in a comment thread.
Other than that, it's the same as creating a regular comment.

As for the `parentCommentId`, this could be supplied as an HTTP parameter, but I think a better, more idiomatic way to do this is to include the parameter in the endpoint URL.
And like regular comments, I think the best HTTP verb for this is `POST`.
So, the REST endpoint will be `POST /api/listing/{listingId}/comment/{commentId}/reply`.
And it will require a request body in JSON like so:
```
{
    "content": "..."
}
```

## API examples w/ JSON stubs

### Get all comments for a listing
Explanation: this example gets all the comments for the listing with ID 24

Request endpoint: `GET /api/listing/24/comment`

Request body: N/A

Response:
```
{
    "comments": [
        {
            "commentId": 2923,
            "authorName": "Gavin D'Hondt",
            "content": "Hey, do you think you could go $10 cheaper? I'd be willing to by it then",
            "createdAt": "2024-10-16T21:47:18Z",
            "parentCommentId": null
        },
        {
            "commentId": 2940,
            "authorName": "Jane Doe",
            "content": "Hmm I don't think so, sorry. I think it's already priced pretty fairly.",
            "createdAt": "2024-10-17T05:31:42Z",
            "parentCommentId": 2923
        },
        {
            "commentId": 2967,
            "authorName": "Edward Cullen",
            "content": "These pictures are kinda sus ngl ඞ but I'll buy it. DM me.",
            "createdAt": "2024-10-18T14:03:10Z",
            "parentCommentId": null
        }
    ]
}
```

### Post a comment on a listing
Explanation: this example posts a comment that says "Hey, do you think you could go $10 cheaper? I'd be willing to by it then" on the listing with the ID 24.

Request endpoint: `POST /api/listing/24/comment`

Request body:
```
{
    "content": "Hey, do you think you could go $10 cheaper? I'd be willing to by it then"
}
```

Response: N/A

### Reply to a comment on a listing
Explanation: this example replies to the comment with the ID 89 on the listing with ID 24 that says "Hmm I don't think so, sorry. I think it's already priced pretty fairly."

Request endpoint: `POST /api/listing/24/comment/89/reply`

Request body:
```
{
    "content": "Hmm I don't think so, sorry. I think it's already priced pretty fairly."
}
```

Response: N/A

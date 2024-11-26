import { NavBar } from "@/components/NavBar";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Textarea } from "@/components/ui/textarea";
import { mockGetAllCommentsForListingResponse, mockGetListingByIdResponse } from "@/lib/api/mocks";
import { CommentWithCreator } from "@/lib/api/model";
import { LucideCalendar } from "lucide-react";
import { useState } from "react";
import { useParams } from "react-router-dom";

interface CommentCardProps {
    comment: CommentWithCreator;
    commentById: Record<string, CommentWithCreator>;
}

function CommentCard({ comment, commentById }: CommentCardProps) {
    return (
        <Card key={`comment-${comment.id}`} className="w-full">
            <CardContent className="py-4 px-6 flex flex-col gap-2">
                <div>
                    <p className="font-semibold text-wrap">{comment.creator.firstName} {comment.creator.lastName}</p>
                    <p className="text-sm text-muted-foreground text-wrap inline-flex flex-row items-center gap-1">
                        <LucideCalendar size="16" /> Posted {new Date(comment.createdAt).toLocaleString()}
                    </p>
                </div>

                {comment.parentCommentId !== undefined && (
                    <p className="font-semibold text-muted-foreground text-sm">(In reply to {commentById[comment.parentCommentId].creator.firstName} {commentById[comment.parentCommentId].creator.lastName})</p>
                )}
                <p className="text-wrap">{comment.content}</p>
            </CardContent>
        </Card>
    );
}

export function ViewListingPage() {
    const params = useParams();

    console.log(params["id"]);

    const listing = mockGetListingByIdResponse;
    const [comments, setComments] = useState(mockGetAllCommentsForListingResponse);

    const commentById = Object.fromEntries(comments.map((comment) => [comment.id, comment]));

    const [commentText, setCommentText] = useState("");

    return (
        <div>
            <NavBar />

            <div className="px-4 md:px-0 md:w-3/4 lg:w-3/5 mx-auto flex flex-col gap-8">
                <div>
                    <h2 className="text-4xl sm:text-5xl font-semibold">{listing.title}</h2>
                    <p className="text-muted-foreground">By: {listing.authorName}</p>
                </div>

                <div className="w-full h-52 bg-gray-400 rounded-lg" />

                <div>
                    <h3 className="text-3xl font-semibold">Sale Information</h3>
                    <p><span className="font-medium">Description:</span> {listing.description}</p>
                    <p><span className="font-medium">Condition:</span> {listing.condition}</p>
                    <p><span className="font-medium">Price:</span> ${listing.price}</p>
                    <p><span className="font-medium">Availability:</span> {listing.availability}</p>
                    <p><span className="font-medium">Class Subject:</span> {listing.classSubject}</p>
                </div>

                <div>
                    <h3 className="text-3xl font-semibold">Comments</h3>
                        
                    <div className="flex flex-col gap-4">
                        <div className="flex flex-col gap-2">
                            {comments.map((comment) => (
                                <CommentCard key={`comment-${comment.id}`} comment={comment} commentById={commentById} />
                            ))}
                        </div>

                        <div className="flex flex-col gap-2 items-center">
                            <Textarea
                                placeholder="Add a comment..."
                                value={commentText}
                                onChange={(e) => setCommentText(e.target.value)}
                            />

                            <Button>Add comment</Button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
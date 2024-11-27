import { NavBar } from "@/components/NavBar";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Textarea } from "@/components/ui/textarea";
import { createComment, getAllComments, getListing } from "@/lib/api/apis";
import { CommentWithCreator } from "@/lib/api/model";
import { useQuery } from "@tanstack/react-query";
import { LucideCalendar, LucideLoader2 } from "lucide-react";
import React, { useEffect, useMemo, useState } from "react";
import { useParams } from "react-router-dom";

interface CommentCardProps {
    comment: CommentWithCreator;
    commentById: Record<string, CommentWithCreator>;
    setCommentText: React.Dispatch<React.SetStateAction<string>>
}

function CommentCard({ comment, commentById, setCommentText }: CommentCardProps) {
    return (
        <Card key={`comment-${comment.id}`} className="w-full">
            <CardContent className="py-4 px-6 flex flex-col gap-2">
                <div>
                    <p className="font-semibold text-wrap">{comment.creator.firstName} {comment.creator.lastName} &lt;<a href={`mailto:${comment.creator.email}`} className="underline">{comment.creator.email}</a>&gt;</p>
                    <p className="text-sm text-muted-foreground text-wrap inline-flex flex-row items-center gap-1">
                        <LucideCalendar size="16" /> Posted {new Date(comment.createdAt).toLocaleString()}
                    </p>
                </div>

                {comment.parentCommentId && (
                    <p className="font-semibold text-muted-foreground text-sm">(In reply to {commentById[comment.parentCommentId].creator.firstName} {commentById[comment.parentCommentId].creator.lastName})</p>
                )}
                <p className="text-wrap">{comment.content}</p>

                <Button
                    className="w-fit"
                    onClick={() => setCommentText((prev) => {
                        const replyFormat = /<@\d+>/;
                        if (replyFormat.test(prev)) {
                            return prev.replace(replyFormat, `<@${comment.id}>`);
                        }

                        return `<@${comment.id}> ` + prev;
                    })}
                >
                    Reply
                </Button>
            </CardContent>
        </Card>
    );
}

export function ViewListingPage() {
    const params = useParams();

    console.log(params["id"]);

    const { isLoading: isListingLoading, error: listingError, data: getListingResponse } = useQuery({
        queryKey: ["GetListingById", { id: params["id"] }],
        queryFn: () => getListing({ listingId: parseInt(params["id"]!) })
    })
    const listing = getListingResponse?.listing;

    const { isLoading: isCommentsLoading, error: commentsError, data: getCommentsResponse } = useQuery({
        queryKey: ["GetAllCommentsForListing", { listingId: params["id"] }],
        queryFn: () => getAllComments({ listingId: parseInt(params["id"]!) })
    });

    const [comments, setComments] = useState<CommentWithCreator[]>([]);
    useEffect(() => {
        if (getCommentsResponse) {
            setComments(getCommentsResponse.comments);
        }
    }, [getCommentsResponse]);

    const commentById = useMemo(
        () => Object.fromEntries(comments.map((comment) => [comment.id, comment])),
        [comments]
    );

    const [newCommentText, setNewCommentText] = useState("");

    return (
        <div>
            <NavBar />
                {isListingLoading ? (
                    <LucideLoader2 className="animate-spin w-16 h-16" />
                ) : listingError ? (
                    <p>We encountered a fatal error when trying to read the listings. Please try again later.</p>
                ) : listing && (
                    <div className="px-4 md:px-0 md:w-3/4 lg:w-3/5 mx-auto flex flex-col gap-8">
                        <div>
                            <h2 className="text-4xl sm:text-5xl font-semibold">{listing.title}</h2>
                            <p className="text-muted-foreground">By: {listing.authorName}</p>
                        </div>

                        <div className="w-full h-52 bg-gray-400 rounded-lg" />

                        <div>
                            <h3 className="text-3xl font-semibold">Sale Information</h3>
                            <p><span className="font-medium">
                                Seller:</span> {listing.seller.firstName} {listing.seller.lastName} &lt;<a href={`mailto:${listing.seller.email}`} className="underline">{listing.seller.email}</a>&gt;
                            </p>
                            <p><span className="font-medium">Description:</span> {listing.description}</p>
                            <p><span className="font-medium">Condition:</span> {listing.condition}</p>
                            <p><span className="font-medium">Price:</span> ${listing.price}</p>
                            <p><span className="font-medium">Availability:</span> {listing.availability}</p>
                            <p><span className="font-medium">Class Subject:</span> {listing.classSubject}</p>
                        </div>

                        <div>
                            <h3 className="text-3xl font-semibold">Comments</h3>
                                
                            {isCommentsLoading ? (
                                <LucideLoader2 className="animate-spin w-12 h-12" />
                            ) : commentsError ? (
                                <p>We encountered a fatal error when trying to read the comments. Please try again later.</p>
                            ) : (
                                <div className="flex flex-col gap-4">
                                    <div className="flex flex-col gap-2">
                                        {comments.map((comment) => (
                                            <CommentCard key={`comment-${comment.id}`} comment={comment} commentById={commentById} setCommentText={setNewCommentText} />
                                        ))}
                                    </div>

                                    <div className="flex flex-col gap-2 items-center">
                                        <Textarea
                                            placeholder="Add a comment..."
                                            value={newCommentText}
                                            onChange={(e) => setNewCommentText(e.target.value)}
                                        />

                                        <Button
                                            onClick={async () => {
                                                let formattedNewCommentText = newCommentText;
                                                let parentCommentId: number | undefined = undefined;
                                                if (newCommentText.startsWith("<@")) {
                                                    const endBracketIndex = newCommentText.indexOf(">");
                                                    if (endBracketIndex === -1) {
                                                        alert("Invalid comment format! Please try again.");
                                                        return;
                                                    }

                                                    const commentId = parseInt(newCommentText.substring(2, endBracketIndex));
                                                    if (isNaN(commentId)) {
                                                        alert("Invalid comment format! Please try again.");
                                                        return;
                                                    }

                                                    if (!commentById[commentId]) {
                                                        alert("Invalid comment format! Please try again.");
                                                        return;
                                                    }

                                                    parentCommentId = commentId;
                                                    formattedNewCommentText = newCommentText.substring(endBracketIndex + 1).trim();
                                                }

                                                if (!formattedNewCommentText) {
                                                    alert("Please enter a comment!");
                                                    return;
                                                }

                                                const response = await createComment({
                                                    listingId: listing.id,
                                                    content: formattedNewCommentText,
                                                    parentCommentId
                                                });

                                                if (!response || !response.comment) {
                                                    alert("Failed to add comment! Please try again later.");
                                                    return;
                                                }

                                                setComments((prev) => [response.comment, ...prev]);
                                                setNewCommentText("");

                                                console.log("here");
                                            }}
                                        >
                                            Add comment
                                        </Button>
                                    </div>
                                </div>
                            )}
                        </div>
                    </div>
                )}
        </div>
    );
}
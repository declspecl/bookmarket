import { NavBar } from "@/components/NavBar";
import "./LoginPage.css";
import { useParams, useSearchParams } from "react-router-dom";

export function ViewListingPage() {
    const params = useParams();

    return (
        <div>
            <NavBar />

            <h1>View Listing for ID {params["id"]}</h1>
        </div>
    );
}
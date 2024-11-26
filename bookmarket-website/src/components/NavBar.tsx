import "./NavBar.css";

import { Link, useNavigate } from "react-router-dom";

export function NavBar() {
    const cookies = document.cookie;
    const isLoggedIn = cookies.includes("session");
    
    const navigate = useNavigate();

    return (
        <div>
            <Link to="/">Shop</Link>
            <Link to="/create-listing">Create Listing</Link>

            {!isLoggedIn ? (
                <Link to="/login">Login</Link>
             ) : (
                <button
                    onClick={async () => {
                        await fetch("/api/auth/logout", {
                            method: "POST"
                        });
                        document.cookie = "session=;Max-Age=0";

                        navigate("/");
                    }}
                >
                    Logout
                </button>
             )}
        </div>
    );
}
import "./NavBar.css";

import { Link, useNavigate } from "react-router-dom";

export function NavBar() {
    const cookies = document.cookie;
    const isLoggedIn = cookies.includes("session");
    
    const navigate = useNavigate();

    return (
        <nav className = "navbar"> 
            <img
                src="/path-to-your-image.png"
                className="navbar-logo"
            />
                <h1 className="navbar-title">O.U. Bookmarket</h1>
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

                            navigate("/");
                        }}
                    >
                        Logout
                    </button>
                )}
            </div>
        </nav>

    );
}
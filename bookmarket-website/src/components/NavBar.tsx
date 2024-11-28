import { logout } from "@/lib/api/apis";
import "./NavBar.css";

import { Link, useNavigate } from "react-router-dom";

export function NavBar() {
    const cookies = document.cookie;
    const isLoggedIn = cookies.includes("session");
    
    const navigate = useNavigate();

    return (
        <nav className="navbar"> 
            <div className="flex flex-col items-center gap-2 sm:flex-row">
                <img loading="eager" src="/OU_BookmarketImage.png" alt="logo" className="navbar-logo"/>
                <h1 className="navbar-title">O.U. Bookmarket</h1>
            </div>

            <div className="flex flex-row items-center">
                <Link to="/">Shop</Link>

                {isLoggedIn && <Link to="/create-listing">Create Listing</Link>}

                {!isLoggedIn ? (
                    <Link to="/login">Login</Link>
                ) : (
                    <button
                        className="hover:underline"
                        onClick={async () => {
                            await logout();
                            document.cookie = "session=; Max-Age=0; Path=/";

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
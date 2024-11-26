import "./LoginPage.css";

import { NavBar } from "@/components/NavBar";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { login } from "@/lib/api/apis";

export function LoginPage() {
    const [inputtedEmail, setInputtedEmail] = useState("");
    const [inputtedPassword, setInputtedPassword] = useState("");

    const navigate = useNavigate();

    return (
        <div className="login-page">
            <NavBar />

            <h1 className="login-page-title">Login</h1>

            {/*Sign-up Prompt*/}
            <p className="signup-prompt">
                Don't have an account? <Link to="/signup" className="underline">Click here</Link> to sign up!
            </p>

            <div className="flex flex-col gap-6">
                {/*Email Field*/}
                <div>
                    <h2 className="title">Email Address</h2>
                    <Input
                        type="email"
                        id="email"
                        name="email"
                        placeholder="Enter your email"
                        value={inputtedEmail}
                        onChange={(e) => setInputtedEmail(e.target.value)}
                        required
                    />
                </div>

                {/*Password Field*/}
                <div>
                    <h2 className="title">Password</h2>
                    <Input
                        type="password"
                        id="password"
                        name="password"
                        placeholder="Enter your password"
                        value={inputtedPassword}
                        onChange={(e) => setInputtedPassword(e.target.value)}
                        required
                    />
                </div>

                {/*Submit Button*/}
                <div className="submit-box">
                    <Button onClick={async () => {
                        if (!inputtedEmail || !inputtedPassword) {
                            alert("Please fill in all fields.");
                            return;
                        }

                        if (!inputtedEmail.match("^[^\s@]+@([^\s@.,]+\.)+[^\s@.,]{2,}$")) {
                            alert("Please enter a valid email address.");
                            return;
                        }

                        try {
                            const response = await login({
                                email: inputtedEmail,
                                password: inputtedPassword
                            });

                            if (response.status.toString().startsWith("5")) {
                                alert("Failed to log in. Please try again later.");
                                return;
                            }
                            else if (!response.ok) {
                                alert("Invalid credentials provided.");
                                return;
                            }

                            navigate("/");
                        }
                        catch (e) {
                            alert(`Error: ${e}`);
                        }
                    }}>
                        Login
                    </Button>
                </div>
            </div>
        </div>
    );
}

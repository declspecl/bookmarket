import "./SignUpPage.css";

import { NavBar } from "@/components/NavBar";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { signup } from "@/lib/api/apis";

export function SignUpPage() {
    const [inputtedFirstName, setInputtedFirstName] = useState("");
    const [inputtedLastName, setInputtedLastName] = useState("");
    const [inputtedEmail, setInputtedEmail] = useState("");
    const [inputtedPassword, setInputtedPassword] = useState("");
    const [inputtedConfirmPassword, setInputtedConfirmPassword] = useState("");

    const navigate = useNavigate();

    return (
        <div className="signup-page">
            <NavBar />

            <h1 className="signup-page-title">Sign Up</h1>

            <p className="login-prompt">
                Already have an account? <Link to="/login" className="underline">Click here</Link> to login!
            </p>

            <div className="flex flex-col gap-6">
                {/*First Name*/}
                <div>
                    <h2 className="title">Fist Name</h2>
                    <Input
                        type="text"
                        id="firstName"
                        name="firstName"
                        placeholder="Enter your first name"
                        value={inputtedFirstName}
                        onChange={(e) => setInputtedFirstName(e.currentTarget.value)}
                        required
                    />
                </div>

                {/*Last Name*/}
                <div>
                    <h2 className="title">Last Name</h2>
                    <Input
                        type="text"
                        id="lastName"
                        name="lastName"
                        placeholder="Enter your last name"
                        value={inputtedLastName}
                        onChange={(e) => setInputtedLastName(e.currentTarget.value)}
                        required
                    />
                </div>

                {/*Email Address*/}
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

                {/*Password*/}
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

                {/*Confirm Password*/}
                <div>
                    <h2 className="title">Confirm Password</h2>
                    <Input
                        type="password"
                        id="confirmPassword"
                        name="confirmPassword"
                        placeholder="Confirm your password"
                        value={inputtedConfirmPassword}
                        onChange={(e) => setInputtedConfirmPassword(e.target.value)}
                        required
                    />
                </div>

                {/*Submit Button*/}
                <div
                    className="submit-box"
                    onClick={async () => {
                        if (!inputtedFirstName || !inputtedLastName || !inputtedEmail || !inputtedPassword || !inputtedConfirmPassword) {
                            alert("Please fill in all fields.");
                            return;
                        }

                        if (!inputtedEmail.match(/^[^\s@]+@([^\s@.,]+\.)+[^\s@.,]{2,}$/)) {
                            alert("Please enter a valid email address.");
                            return;
                        }

                        if (inputtedPassword !== inputtedConfirmPassword) {
                            alert("Passwords do not match.");
                            return;
                        }

                        try {
                            const response = await signup({
                                firstName: inputtedFirstName,
                                lastName: inputtedLastName,
                                email: inputtedEmail,
                                password: inputtedPassword
                            });

                            if (response.status.toString().startsWith("5")) {
                                alert("Failed to sign up. Please try again later.");
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

                    }}
                >
                    <Button>Sign Up</Button>
                </div>
            </div>
        </div>
    );
}

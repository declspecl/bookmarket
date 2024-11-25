import "./SignUpPage.css";

import { NavBar } from "@/components/NavBar";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useState } from "react";

export function SignUpPage() {
    const [inputtedFullName, setInputtedFullName] = useState("");
    const [inputtedEmail, setInputtedEmail] = useState("");
    const [inputtedPassword, setInputtedPassword] = useState("");
    const [inputtedConfirmPassword, setInputtedConfirmPassword] = useState("");

    return (
        <div className="signup-page">
            <NavBar />

            <h1 className="page-title">Sign Up</h1>

            <div className="flex flex-col gap-6">
                {/*Full Name*/}
                <div>
                    <h2 className="title">Full Name</h2>
                    <Input
                        type="text"
                        id="fullName"
                        name="fullName"
                        placeholder="Enter your full name"
                        value={inputtedFullName}
                        onChange={(e) => setInputtedFullName(e.target.value)}
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
                <div className="submit-box">
                    <Button>Sign Up</Button>
                </div>
            </div>
        </div>
    );
}

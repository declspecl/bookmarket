import "./LoginPage.css";

import { NavBar } from "@/components/NavBar";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useState } from "react";

export function LoginPage() {
    const [inputtedEmail, setInputtedEmail] = useState("");
    const [inputtedPassword, setInputtedPassword] = useState("");

    return (
        <div className="login-page">
            <NavBar />

            <h1 className="page-title">Login</h1>

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
                    <Button>Login</Button>
                </div>
            </div>
        </div>
    );
}

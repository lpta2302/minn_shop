import { useForm } from "react-hook-form";
import { z } from "zod";
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Link, useNavigate } from "react-router"
import { zodResolver } from "@hookform/resolvers/zod";
import { useSignin } from "@/tanstack/queries/authQueries";
import { useEffect } from "react";
import { setLocalstorage } from "@/lib/localstorage";
import { useAuthContext } from "@/context/AuthContext";


const registerSchema = z.object({
    email: z.email("Invalid email string"),
    password: z.string().min(6, "Password must be at least 6 characters"),
    role: z.enum(["CUSTOMER"])
})

export type SignInRequest = z.infer<typeof registerSchema>

function Signin() {
    const {
        register: signInData,
        handleSubmit,
        formState: { errors }
    } = useForm<SignInRequest>({
        resolver: zodResolver(registerSchema),
        defaultValues: {
            role: "CUSTOMER"
        }
    });

    const {isAuthenticated} = useAuthContext()

    const navigate = useNavigate()

    if (isAuthenticated) {
        navigate("/")
    }

    const {mutateAsync: signIn, isPending, data: authResponse} = useSignin();

    const onSubmit = async (data: SignInRequest) => {
        const {accessToken} = await signIn(data)
        if (accessToken) {
            setLocalstorage('accessToken', accessToken)
            navigate('/')
        }
    };

    useEffect(() => {
    console.log(authResponse);
        
    }, [authResponse]);
    return (
        <form noValidate onSubmit={handleSubmit(onSubmit)} className="max-w-lg mx-auto py-16 px-4 space-y-6">
            {/* Logo Section */}
            <div className="flex justify-center items-center gap-4">
                Logo
            </div>

            {/* Heading */}
            <h1 className="text-2xl leading-tight">
                Welcome back!
            </h1>

            {/* Email */}
            <div className="space-y-1">
                <Label htmlFor="email" className="sr-only">
                    Email
                </Label>
                <Input
                    id="email"
                    type="email"
                    placeholder="Email *"
                    className="rounded-lg px-4 py-6 text-base"
                    {...signInData("email")}
                />
                {errors.email && (
                    <p className="text-sm text-red-500">{errors.email.message}</p>
                )}
            </div>

            {/* Password */}
            <div className="space-y-1">
                <Label htmlFor="password" className="sr-only">
                    Password
                </Label>
                <Input
                    id="password"
                    type="password"
                    placeholder="Password *"
                    className="rounded-lg px-4 py-6 text-base"
                    {...signInData("password")}
                />
                {errors.password && (
                    <p className="text-sm text-red-500">{errors.password.message}</p>
                )}
            </div>

            {/* Terms */}
            <p className="text-muted-foreground text-left">
                By continuing, I agree to Nike's{" "}
                <a href="#" className="underline">
                    Privacy Policy
                </a>{" "}
                and{" "}
                <a href="#" className="underline">
                    Terms of Use
                </a>
                .
            </p>

            {/* Continue Button */}
            <div className="flex flex-col w-full items-center gap-3 mt-8">
                <Button className="bg-black w-1/2 min-w-sm text-white px-6 py-6 text-base rounded-full" disabled={isPending}>
                    { isPending ? "Signing in..." : "Sign In"}
                </Button>
                <p className="text-muted-foreground">haven't got an account?</p>
                <Link to="/register">
                    <Button className="w-1/2 min-w-sm px-6 py-6 rounded-full" variant={"outline"}>
                        Register
                    </Button>
                </Link>
            </div>
        </form>)
}

export default Signin
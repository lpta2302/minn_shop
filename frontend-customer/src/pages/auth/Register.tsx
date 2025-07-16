import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { Link, useNavigate } from "react-router";
import { useRegister } from "@/tanstack/queries/authQueries";
import { useAuthContext } from "@/context/AuthContext";
import { setLocalstorage } from "@/lib/clientStorage";

const registerSchema = z.object({
    email: z.email("Invalid email string"),
    password: z.string().min(6, "Password must be at least 6 characters"),
    confirmPassword: z.string().min(6, "Password must be at least 6 characters"),
    firstName: z.string().min(2, "First name must be 2-200 characters").max(200),
    lastName: z.string().min(2, "Last name must be 2-200 characters").max(200),
    role: z.enum(["CUSTOMER"])
}).refine((data) => data.password === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"]
});

export type RegisterRequest = z.infer<typeof registerSchema>;

export default function Register() {
    const {
        register: registerData,
        handleSubmit,
        formState: { errors }
    } = useForm<RegisterRequest>({
        resolver: zodResolver(registerSchema),
        defaultValues: {
            role: "CUSTOMER"
        }
    });

    const { mutateAsync: register, isPending } = useRegister();

    const { isAuthenticated } = useAuthContext()

    const navigate = useNavigate()

    if (isAuthenticated) {
        navigate("/")
    }

    const onSubmit = async (data: RegisterRequest) => {
        const { accessToken } = await register(data)
        if (accessToken) {
            setLocalstorage('accessToken', accessToken)
            navigate('/')
        }
    };

    return (
        <form
            onSubmit={handleSubmit(onSubmit)}
            className="max-w-lg mx-auto py-16 px-4 space-y-6"
            noValidate
        >
            {/* Logo */}
            <div className="flex justify-center items-center gap-4">Logo</div>

            <h1 className="text-2xl leading-tight">Enter your info to join us.</h1>

            {/* First & Last Name */}
            <div className="flex gap-2">
                <div className="w-1/2 space-y-1">
                    <Label htmlFor="firstName" className="sr-only">
                        First Name
                    </Label>
                    <Input
                        id="firstName"
                        placeholder="First Name *"
                        className="rounded-lg px-4 py-6 text-base"
                        {...registerData("firstName")}
                    />
                    {errors.firstName && (
                        <p className="text-sm text-red-500">{errors.firstName.message}</p>
                    )}
                </div>
                <div className="w-1/2 space-y-1">
                    <Label htmlFor="lastName" className="sr-only">
                        Last Name
                    </Label>
                    <Input
                        id="lastName"
                        placeholder="Last Name *"
                        className="rounded-lg px-4 py-6 text-base"
                        {...registerData("lastName")}
                    />
                    {errors.lastName && (
                        <p className="text-sm text-red-500">{errors.lastName.message}</p>
                    )}
                </div>
            </div>

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
                    {...registerData("email")}
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
                    {...registerData("password")}
                />
                {errors.password && (
                    <p className="text-sm text-red-500">{errors.password.message}</p>
                )}
            </div>

            {/* Confirm Password */}
            <div className="space-y-1">
                <Label htmlFor="confirm-password" className="sr-only">
                    Confirm password
                </Label>
                <Input
                    id="confirm-password"
                    type="password"
                    placeholder="Confirm password *"
                    className="rounded-lg px-4 py-6 text-base"
                    {...registerData("confirmPassword")}
                />
                {errors.confirmPassword && (
                    <p className="text-sm text-red-500">{errors.confirmPassword?.message}</p>
                )}
            </div>

            {/* Role (hidden or select if you want to choose) */}
            <input type="hidden" value="CUSTOMER" {...registerData("role")} />
            {errors.role && (
                <p className="text-sm text-red-500">{errors.role.message}</p>
            )}

            {/* Terms */}
            <p className="text-muted-foreground text-left text-sm">
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

            {/* Submit */}
            <div className="flex flex-col w-full items-center gap-3 mt-8">
                <Button
                    type="submit"
                    className="bg-black w-1/2 min-w-sm text-white px-6 py-6 text-base rounded-full"
                    disabled={isPending}
                >
                    {isPending ? "Registering..." : "Register"}
                </Button>
                <p className="text-muted-foreground">Already have an account?</p>
                <Link to="/sign-in">
                    <Button className="w-1/2 min-w-sm px-6 py-6 rounded-full" variant="outline">
                        Sign In
                    </Button>
                </Link>
            </div>
        </form>
    );
}

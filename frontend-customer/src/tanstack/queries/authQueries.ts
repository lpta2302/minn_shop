import { useMutation, useQuery } from "@tanstack/react-query";
import { get, post } from "../api/crud";
import type { RegisterRequest } from "@/pages/auth/Register";
import type { SignInRequest } from "@/pages/auth/Signin";
import type { Customer } from "@/types/auth";

export interface AuthResponse{
    accessToken: string
    refreshToken: string
}

export function useGetCurrentUser() {
    return useQuery<Customer>({
        queryKey: ["GET_CURRENT_USER"],
        queryFn: ()=>get('/customers/current-user'),
        enabled: false
    })
}

export function useRefreshToken() {
    return useMutation<AuthResponse, Error, string>({
        mutationFn: (refreshToken: string) => post<AuthResponse>('/auth/refresh-token', refreshToken),
    })
}

export function useRegister() {
    return useMutation<AuthResponse, Error, RegisterRequest>({
        mutationFn: (registerRequest: RegisterRequest) => post<AuthResponse>('/auth/register', registerRequest),
    })
}

export function useSignin() {
    return useMutation<AuthResponse, Error, SignInRequest>({
        mutationFn: (signInRequest: SignInRequest) => post<AuthResponse>('/auth/authenticate', signInRequest),
    })
}
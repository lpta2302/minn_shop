import { useMutation, useQuery } from "@tanstack/react-query";
import { get, patch } from "../api/crud";
import type { Cart, CartItemRequest } from "@/types/cart";
import { GET_OWN_CART } from "../keys/cartKeys";

export function useAddToCart() {
    return useMutation({
        mutationFn: (cartItem: CartItemRequest) => patch('/carts/items', cartItem),
        
    })
}

export function useGetOwnCart(isAuthenticated: boolean) {
    return useQuery<Cart>({
        queryKey: [GET_OWN_CART],
        queryFn: ()=> get("/carts/own"),
        enabled: isAuthenticated
    })
}
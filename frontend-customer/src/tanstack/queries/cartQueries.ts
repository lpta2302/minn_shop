import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
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

export function useAddItem(){
    const queryClient = useQueryClient()

    return useMutation({
        mutationFn: (newItem: CartItemRequest) => patch('/carts/items', newItem),
        onSuccess: ()=>{
            queryClient.invalidateQueries({ queryKey: [GET_OWN_CART] });
        }
    })
}

export function useUpdateItem(){
    const queryClient = useQueryClient()

    return useMutation({
        mutationFn: (newItem: CartItemRequest) => patch('/carts/items/'+newItem.id, newItem),
        onSuccess: ()=>{
            queryClient.invalidateQueries({ queryKey: [GET_OWN_CART] });
        }
    })
}
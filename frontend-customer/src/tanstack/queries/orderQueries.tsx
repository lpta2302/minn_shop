import type { OrderRequest } from "@/types/order";
import { useMutation } from "@tanstack/react-query";
import { post } from "../api/crud";
import { toast } from "sonner";
import { CircleXIcon } from "lucide-react";

export function usePlaceOrder() {
    return useMutation({
        mutationFn: (order: OrderRequest) => post('/orders', order),
        onError: (error)=>{
            toast(error.message,{
                className:"text-center text-red-500",
                icon: <CircleXIcon/>
            })
        }
    })
}
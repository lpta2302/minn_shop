import { useQuery } from "@tanstack/react-query";
import { getAll } from "../api/crud";
import { GET_ALL_DISPLAYED_PRODUCT_VARIANTS } from "../keys/productVariantKeys";

export function useGetAllDisplayedProductVariants() {
    return useQuery({
        queryKey: [GET_ALL_DISPLAYED_PRODUCT_VARIANTS],
        queryFn: ()=>getAll('/product-variants'),
    })
}
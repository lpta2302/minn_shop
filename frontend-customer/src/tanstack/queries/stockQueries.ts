import type { Stock } from "@/types/stock";
import { useQuery } from "@tanstack/react-query";
import { GET_ALL_STOCK_BY_VARIANT_ID } from "../keys/stockKeys";
import { get } from "../api/crud";

export function useGetStocksByVariantId(variantId: number | undefined) {
    return useQuery<Stock[]>({
        queryKey: [GET_ALL_STOCK_BY_VARIANT_ID, variantId],
        queryFn: () => get('/stocks/' + variantId),
        enabled: !!variantId
    })
}
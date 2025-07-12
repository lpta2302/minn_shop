import { type PageResponse } from '../../types/pageResponse';
import { useQuery } from "@tanstack/react-query";
import { get } from "../api/crud";
import { GET_ALL_DISPLAYED_CATEGORIES } from "../keys/categoryKeys";
import type { Category } from '@/types/category';

export function useGetAllDisplayedCategories() {
    return useQuery<PageResponse<Category>>({
        queryKey: [GET_ALL_DISPLAYED_CATEGORIES],
        queryFn: ()=>get('/categories/displayed'),
    })
}
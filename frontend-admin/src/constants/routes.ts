import CategoryManagement from "@/pages/category/CategoryManagement"
import StockManagement from "@/pages/stock/StockManagement"

export interface RouteType {
    path: string
    component: React.ComponentType<unknown>
}

export const managementRoutes: RouteType[] = [
    {
        path: "category",
        component: CategoryManagement
    },
    {
        path: "stock",
        component: StockManagement
    }
]
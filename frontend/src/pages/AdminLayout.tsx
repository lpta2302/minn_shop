import { AppSidebar } from "@/components"
import { SidebarInset, SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar"
import { Separator } from "@radix-ui/react-dropdown-menu"
import { Outlet } from "react-router"

function AdminLayout() {
    return (
        <SidebarProvider>
            <AppSidebar />
            <SidebarInset>
                <header className="flex h-16 shrink-0 items-center gap-2 border-b px-4">
                    <SidebarTrigger className="-ml-1" />
                    <Separator
                        className="mr-2 data-[orientation=vertical]:h-4"
                    />
                </header>
                <Outlet/>
            </SidebarInset>
        </SidebarProvider>
    )
}

export default AdminLayout
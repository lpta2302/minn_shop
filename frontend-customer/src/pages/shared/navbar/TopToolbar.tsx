import { Button } from "@/components/ui/button"
import MainSearchBar, { type SearchProps } from "../search-bar/MainSearchBar"
import { HeartIcon, ShoppingBagIcon } from "lucide-react"
import { Badge } from "@/components/ui/badge"
import { useCart } from "@/context/CartContext"



function TopToolbar({ setIsSearching, isSearching, setSearchParams }: SearchProps) {
    const { cartItems } = useCart()
    const cartItemQuantity = cartItems.length

    return (
        <div
            className={`
            flex
            items-center 
            gap-4
            ${isSearching && 'flex-1'}
        `}
        >
            <MainSearchBar setSearchParams={setSearchParams} isSearching={isSearching} setIsSearching={setIsSearching} />
            <Button
                className="rounded-full has-[>svg]:p-2"
                variant="ghost">
                <HeartIcon className="size-6" />
            </Button>
            <Button
                className="rounded-full has-[>svg]:p-2 relative"
                variant="ghost">
                <ShoppingBagIcon className="size-6" />
                {
                    cartItemQuantity > 0 &&
                    <Badge
                        className={`
                        rounded-full 
                        px-1 
                        font-mono 
                        tabular-nums 
                        absolute -top-1 -right-1
                        min-w-5
                        h-5
                        ${cartItemQuantity > 99 &&
                            '-right-2'
                            }
                    `}
                        variant="destructive"
                    >
                        {
                            cartItemQuantity > 99 ?
                                "99+" : cartItemQuantity
                        }
                    </Badge>
                }
            </Button>
        </div>
    )
}

export default TopToolbar
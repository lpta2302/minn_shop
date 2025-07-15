import { useGetOwnCart } from "@/tanstack/queries/cartQueries"
import type { CartItem } from "@/types/cart"
import { createContext, useContext, useEffect, useState, type ReactElement } from "react"
import { useAuthContext } from "./AuthContext"

interface CartInfo {
    cartItems: CartItem[]
    addToCart: (newItem: CartItem) => void
}

const initCart = {
    cartItems: [],
    addToCart: () => { }
}
const items: CartItem[] = [
    {
        id: 1,
        quantity: 1,
        productVariant: {
            id: 101,
            variantId: "JORDAN-HOODIE-001",
            slug: "jordan-flight-fleece",
            name: "Jordan Flight Fleece",
            finalPrice: 2449000,
            thumbnail: "https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/23dd8c57-482b-4b97-94b6-4aedd09b03b8/WMNS+NIKE+AIR+MAX+SC.png", // đặt ảnh trong thư mục /public
            status: "ACTIVE",
            productOption: {
                id: 1,
                name: "Size"
            }
        },
        productOptionValue: {
            id: 10,
            name: "S"
        }
    },
    {
        id: 2,
        quantity: 2,
        productVariant: {
            id: 102,
            variantId: "JORDAN-HOODIE-002",
            slug: "jordan-flight-fleece-grey",
            name: "Jordan Flight Fleece Grey",
            finalPrice: 2599000,
            thumbnail: "https://static.nike.com/a/images/t_PDP_1728_v1/w_592,f_auto,q_auto:eco,b_rgb:f5f5f5/14a4c496-e709-47bf-bb24-3497192de192/jordan-flight-fleece-pullover-hoodie-ZL5Dw5.png", // thêm ảnh khác nếu cần
            status: "ACTIVE",
            productOption: {
                id: 1,
                name: "Size"
            }
        },
        productOptionValue: {
            id: 11,
            name: "M"
        }
    }
]

const CartContext = createContext<CartInfo>(initCart)

export const CartProvider = ({ children }: { children: ReactElement }) => {
    const {isAuthenticated} = useAuthContext()
    const [cartItems, setCartItems] = useState<CartItem[] | []>(items);
    const { data: cart } = useGetOwnCart(isAuthenticated);

    const addToCart = (newItem: CartItem) => {
        setCartItems((prev) => {
            const exists = prev.find((item) =>
                item.productVariant.id === newItem.productVariant.id
                && item.productOptionValue.id === newItem.productOptionValue.id
            );
            if (!exists) {
                return [...prev, newItem];
            }
            return prev;
        });
    };

    useEffect(() => {
        setCartItems(cart?.items ? cart.items : items)
    }, [cart]);

    return (
        <CartContext.Provider value={{ addToCart, cartItems }}>
            {children}
        </CartContext.Provider>
    );
};

// eslint-disable-next-line react-refresh/only-export-components
export const useCart = () => useContext(CartContext);
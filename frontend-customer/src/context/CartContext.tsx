import { useGetOwnCart } from "@/tanstack/queries/cartQueries"
import type { CartItem } from "@/types/cart"
import { createContext, useContext, useEffect, useState, type ReactElement } from "react"

interface CartInfo {
    cartItems: CartItem[]
    addToCart: (newItem: CartItem) => void
}

const initCart = {
    cartItems: [],
    addToCart: () => { }
}

const CartContext = createContext<CartInfo>(initCart)

export const CartProvider = ({ children }: { children: ReactElement }) => {
    const [cartItems, setCartItems] = useState<CartItem[] | []>([]);
    const { data: cart } = useGetOwnCart();

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
        setCartItems(cart?.items ? cart.items : [])
    }, [cart]);

    return (
        <CartContext.Provider value={{ addToCart, cartItems }}>
            {children}
        </CartContext.Provider>
    );
};

// eslint-disable-next-line react-refresh/only-export-components
export const useCart = () => useContext(CartContext);
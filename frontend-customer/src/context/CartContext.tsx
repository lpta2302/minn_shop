import { createContext, useContext, useState, type ReactElement } from "react"

interface CartInfo{
    cartItems: number[]
    addToCart: (productVariantId:number)=>void
}

const initCart = {
    cartItems: [],
    addToCart: ()=>{}
}

const CartContext = createContext<CartInfo>(initCart)
 
export const CartProvider = ({ children } : { children: ReactElement}) => {
  const [cartItems, setCartItems] = useState<number[]>([]);

  const addToCart = (productVariantId: number) => {
    setCartItems((prev) => {
      const exists = prev.find((id) => id === productVariantId);
      if (!exists) {
        return [...prev, productVariantId];
      }
      return prev;
    });
  };

  return (
    <CartContext.Provider value={{addToCart, cartItems}}>
      {children}
    </CartContext.Provider>
  );
};

// eslint-disable-next-line react-refresh/only-export-components
export const useCart = () => useContext(CartContext);
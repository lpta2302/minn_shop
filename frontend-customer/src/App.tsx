import { Route, Routes } from "react-router"
import RootLayout from "./pages/RootLayout"
import Homepage from "./pages/home/Homepage"
import ProductDetail from "./pages/product/ProductDetail"
import Cart from "./pages/cart/Cart"
import Signin from "./pages/auth/Signin"
import Register from "./pages/auth/Register"

function App() {
  return (
    <Routes>
        <Route path="/" element={<RootLayout/>}>
            <Route index element={<Homepage/>}/>
            <Route path="product/*" element={<ProductDetail/>}/>
            <Route path="cart" element={<Cart/>}/>
        </Route>
        <Route path="sign-in" element={<Signin/>}/>
        <Route path="register" element={<Register/>}/>
    </Routes>
  )
}

export default App
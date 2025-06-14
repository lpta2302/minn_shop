import { Route, Routes } from "react-router"
import RootLayout from "./pages/RootLayout"
import Homepage from "./pages/home/Homepage"
import ProductDetail from "./pages/product/ProductDetail"

function App() {
  return (
    <Routes>
        <Route path="/" element={<RootLayout/>}>
            <Route index element={<Homepage/>}/>
            <Route path="product/*" element={<ProductDetail/>}/>
        </Route>
    </Routes>
  )
}

export default App
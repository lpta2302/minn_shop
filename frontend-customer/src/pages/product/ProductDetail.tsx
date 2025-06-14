import ProductDetailImageCarousel from "@/components/product/ProductDetailImageCarousel"
import { useEffect } from "react"
import { useLocation, useNavigate } from "react-router"

function ProductDetail() {
    const navigate = useNavigate()
    const location = useLocation()
    const state = location.state

    useEffect(() => {
        if (!state || !state.id || !state.slug) {
            navigate("/", { replace: true })
        }
    }, [state, navigate])

    if (!state || !state.id || !state.slug) return null

    return (
        <div className="flex flex-col space-y-10 px-page_x py-10">
            <section className="flex w-full space-x-8">
                <div className="flex-1">
                    <ProductDetailImageCarousel/>
                </div>
                <div className="flex-1">
                    <h1 className="text-xl">
                        Name
                    </h1>
                    <h1 className="text-subtitle">
                        subtitle write something short
                    </h1>
                </div>
            </section>
            <section>
                Related Products
            </section>
        </div>
    )
}

export default ProductDetail
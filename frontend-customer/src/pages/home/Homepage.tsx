import MainCarousel from "@/components/home/MainCarousel"
import ProductCarousel from "@/components/home/ProductCarousel"
import Loading from "@/components/shared/Loading"
import { useGetAllDisplayedProductVariants } from "@/tanstack/queries/productVariantQueries"
import type { ProductVariant } from "@/types/productVariant"
import { useEffect, useState } from "react"

function Homepage() {
    const {data: allProductsResponse, isLoading, isError} = useGetAllDisplayedProductVariants()
    const [allProducts, setAllProducts] = useState<ProductVariant[] | undefined>(undefined)

    useEffect(() => {
        setAllProducts(allProductsResponse?.content)
    }, [allProductsResponse?.content]);

    if (isLoading || !allProducts) {
        return <Loading/>
    }

    return (
        <div className="flex flex-col space-y-10">
            <section>
                <MainCarousel />
            </section>
            <section className="px-page_x">
                <div>
                    <ProductCarousel title="Sale" products={allProducts}/>
                </div>
            </section>
        </div>
    )
}

export default Homepage
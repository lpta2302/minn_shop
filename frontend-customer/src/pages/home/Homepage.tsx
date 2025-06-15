import MainCarousel from "@/components/home/MainCarousel"
import ProductCarousel from "@/components/home/ProductCarousel"

function Homepage() {
    return (
        <div className="flex flex-col space-y-10">
            <section>
                <MainCarousel/>
            </section>
            <section className="px-page_x">
                <div>
                    <ProductCarousel title="Sale"/>
                </div>
            </section>
        </div>
    )
}

export default Homepage
import { useEffect, useState } from "react"
import { CarouselContent, CarouselItem, Carousel, type CarouselApi } from "../ui/carousel"
import Autoplay from "embla-carousel-autoplay"
import { Circle } from "lucide-react"

function MainCarousel() {
    const [isShowing, setIsShowing] = useState<number>(0)
    const [api, setApi] = useState<CarouselApi>()

    useEffect(() => {
        if (!api) {
            return
        }

        api.on("select", () => {
            const currentSlide = api.selectedScrollSnap()

            setIsShowing(currentSlide)
        })

        const currentSlide = api.selectedScrollSnap()
        setIsShowing(currentSlide)

    }, [api])

    const slides = Array.from({ length: 5 })

    const handleSelectSlide = (index: number) => {
        api?.scrollTo(index)
        setIsShowing(index)
    }

    return (
        <Carousel
            setApi={setApi}
            plugins={
                [
                    Autoplay({
                        delay: 2500,
                        stopOnInteraction: false
                    }),
                ]}
            className="w-full relative">
            <CarouselContent>
                {slides.map((_, index) => (
                    <CarouselItem key={index}>
                        <a href="#">
                            <img
                                className="w-full h-[80vh]"
                                src={`https://picsum.photos/600/350?v=${index}`}
                                alt="Your alt text"
                            />
                        </a>
                    </CarouselItem>
                ))}
            </CarouselContent>
            <div className="
                inline-flex items-center bg-[rgba(35,35,35,0.5)] rounded-sm px-2 space-x-1 py-0.5
                absolute bottom-1 left-1/2    
            ">
                {
                    slides.map((_, index) =>
                        isShowing == index ?
                            <Circle key={index} className="transition-all duration-200" size={13} color="transparent" fill="#dedede" /> :
                            <Circle key={index} className="transition-all duration-200" size={13} color="#dedede" onClick={() => handleSelectSlide(index)} />
                    )
                }
            </div>
        </Carousel>
    )
}

export default MainCarousel
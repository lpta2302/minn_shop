import type { ProductVariantImage } from "@/types/product"

export function getThumbnail(images: ProductVariantImage[]) {
    if (images.length < 0) {
        return
    }

    const thumbnail = images.find(image => image.isThumbnail)
    if (thumbnail) {
        return thumbnail
    } else {
        return images[0]
    }
}
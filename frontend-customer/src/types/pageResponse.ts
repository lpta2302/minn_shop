export interface PageResponse<T>{
    readonly content: T[]
    readonly page: number
    readonly totalPages: number
    readonly totalElements: number
    readonly isFirst: boolean
    readonly isLast: boolean
}
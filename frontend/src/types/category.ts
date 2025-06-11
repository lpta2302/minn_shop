export interface Category {
    code: string
    name: string
    parentId: number | null
    status: 'active' | 'inactive'
}
"use client"

import type { Category } from "@/types"
import { DataTable } from "../shared/data-table/DataTable"
import { categoryColumnDef as columns } from "../column_def/categoryDef"
import { useEffect, useState } from "react"

const categories: Category[] = [
    {
        code: "CAT01",
        name: "Test 1",
        parentId: null,
        status: "active"
    },
    {
        code: "CAT02",
        name: "Test 2",
        parentId: null,
        status: "draft"
    },
]

export default function CategoryTable() {
    const [deletingRow, setDeletingRow] = useState< string | undefined>(undefined)
    const [updatingRow, setUpdatingRow] = useState< string | undefined>(undefined)

    useEffect(()=>(
        console.log(deletingRow)
    ),[deletingRow])
    
    useEffect(()=>(
        console.log(updatingRow)
    ),[updatingRow])

    return (
        <DataTable
            columns={columns}
            data={categories}
            setDeletingRow={setDeletingRow}
            setUpdatingRow={setUpdatingRow}
        />
    )
}

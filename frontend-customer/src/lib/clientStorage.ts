export function getLocalstorage<T>(key: string){
    const storedString = localStorage.getItem(key)
    if (storedString != null) {
        const value: T = JSON.parse(storedString)
        return value
    }
    return null
}

export const setLocalstorage = (key: string, value: unknown) =>{
    localStorage.setItem(key, JSON.stringify(value))
}
export function getSessionStorage<T>(key: string){
    const storedString = sessionStorage.getItem(key)
    if (storedString != null) {
        const value: T = JSON.parse(storedString)
        return value
    }
    return null
}

export const setSessionStorage = (key: string, value: unknown) =>{
    sessionStorage.setItem(key, JSON.stringify(value))
}

export const removeLocalstorage = (key: string) => {
    localStorage.removeItem(key)
}
export const getLocalstorage = (key: string)=>{
    return localStorage.getItem(key)
}

export const setLocalstorage = (key: string, value: string) =>{
    localStorage.setItem(key, value)
}

export const removeLocalstorage = (key: string) => {
    localStorage.removeItem(key)
}
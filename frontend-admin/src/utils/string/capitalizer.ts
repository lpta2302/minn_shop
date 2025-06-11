export const capitalizer = (originalValue: string = ""): string => {
  return originalValue.charAt(0).toUpperCase() + originalValue.slice(1)
}

export const camelcaseGenerator = (originalValue: string = ""): string => {
  return originalValue
    .toLowerCase()
    .split(" ")
    .filter(Boolean)
    .map(capitalizer)
    .join(" ")
}

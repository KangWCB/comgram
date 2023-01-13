import {} from "./postobjReducer";

export const addPostobj = (obj) => ({
    type: "OBJECT_ADD",
    payload: obj,
});

export const updatePostobj = (obj) => ({
    type: "OBJECT_UPDATE",
    payload: obj,
})

export const resetPostobj = () => ({
    type: "OBJECT_RESET",
})


const init = {
    postobj: [],
};

const postobjReducer = (state = init, action) => {
    switch (action.type)
    {
        case "OBJECT_ADD": 
            console.log("objadd")
            let existCheck = false;
            for(var i in state.postobj)
            {
                if(state.postobj[i].id === action.payload.id) existCheck = true;
            };
            if (existCheck)
            {   
                return state;
            }
            else
                return {...state, postobj : state.postobj?.concat(action.payload)}
            
        case "OBJECT_UPDATE":
            console.log("objupdate")
            console.log(action.payload)
            console.log(state)
            return {
                ...state, postobj: action.payload
            }

        case "OBJECT_RESET":
            console.log("reset");
            return init;
        
        default:
            return state;
    };

};

export default postobjReducer;
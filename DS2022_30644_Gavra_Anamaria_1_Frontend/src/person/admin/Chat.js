import * as React from 'react';
import validate from "../components/person-validators";
import { FormGroup, Input, Label} from 'reactstrap';
import * as API_CHAT from "./chat-api";
import SockJsClient from "react-stomp";
import Swal from "sweetalert2";
const SOCKET_URL = 'http://localhost:8080/chat';

class Chat extends React.Component {
    constructor(props) {
        super(props);
        // this.reload = this.reload.bind(this);
         this.toggleForm = this.toggleForm.bind(this);
         //this.reloadHandler = this.props.reloadHandler;
         this.state = {
             interlocutor: (this.props.user==="ana")? "alex":"ana",
        //     isLoaded: false
             //data:  (localStorage.getItem("messages") === null)? []:[{emitator:"ana", msg:localStorage.getItem("messages")}],
             data:  [],
             text: {
                 value: '',
                valid: false,
                touched: false,
                validationRules: {
                minLength: 3,
                    isRequired: true
            }
        },
        //     success: false,
        //     errorStatus: 0,
        //     error: null,
             formIsValid: false,
        //     formControls: userData,
             };
         this.handleChange = this.handleChange.bind(this);
         this.sendMessage = this.sendMessage.bind(this);
        // this.handleSubmit = this.handleSubmit.bind(this);
    }

    toggleForm() {
        this.setState({collapseForm: !this.state.collapseForm});
    }


    sendMessage(){
        console.log("???????_________"+ this.props.user)
        this.setState({
            data: this.state.data.concat([{emitator:this.props.user, msg:this.state.text.value}])
        });
            let d = {emitator:this.props.user, receptor:this.state.interlocutor, text:this.state.text.value};
            document.getElementById("nameField").setAttribute("value","")
            return API_CHAT.postData( d,(result, status, error) => {
                localStorage.setItem("messages", this.state.data.toString());
            })

    }

    handleChange = event => {
        const value = event.target.value;
        const updatedFormElement = this.state.text;
        updatedFormElement.value = value;
        updatedFormElement.touched = true;
        updatedFormElement.valid = validate(value, updatedFormElement.validationRules);
        this.setState({
            text: updatedFormElement,
        });
        let d = {emitator:this.props.user, receptor:this.state.interlocutor, text:"typing"};
        return API_CHAT.postData( d,(result, status, error) => {
            if (result !== null && (status === 200 || status === 201));
            // this.reloadHandler();
            else
                console.log(error);
        })
    };

    showData() {
        let s ="";
        for(let i=0;i<this.state.data.length; i++){
            s=s+this.state.data[i].emitator+": "+this.state.data[i].msg+"      ";
            //document.getElementById("1").
            }
        return s;
    }



    render() {
        let onConnected = () => {
            console.log("Connected2!!")
        }

        function toggleDiv() {
            let x = document.getElementById("myDIV");
            x.style.display = "block";
            setTimeout(function(){
                x.style.display = "none";}, 1000);
        }

        let onMessageReceived = (msg) => {
            let receptor=msg.substring(0, msg.indexOf("text:"));
            let mess = msg.substring( msg.indexOf("text:")+5);
            if(this.props.user===receptor) {
                if (mess === "typing")
                    toggleDiv();
                else {
                    this.setState({
                        data: this.state.data.concat([{emitator: this.state.interlocutor, msg: mess}])
                    });
                    localStorage.setItem("messages", this.state.data.toString());
                }
            }
        }


        return (
            <div className="chat-message clearfix">
                <div id= "1" style={{height:'500px'}}>
                    {this.showData()}<br/>
                    <div id="myDIV" style={{display:"none", paddingTop:"440px"}}>typing...</div>
                </div>
                <div className="input-group mb-0">
                    <Input name='name' id='nameField'
                           onChange={this.handleChange}
                           defaultValue={""}
                           touched={this.state.text.touched ? 1 : 0}
                           valid={this.state.text.valid}
                           required/>
                    <div className="input-group-prepend">
                        <button onClick={this.sendMessage}>send</button>
                    </div>
                </div>
                <SockJsClient
                    url={SOCKET_URL}
                    topics={['/topic2/message']}
                    onConnect={onConnected}
                    onDisconnect={console.log("Disconnected2!")}
                    onMessage={msg => onMessageReceived(msg)}
                    debug={false}
                />
            </div>
        );}
}
export default Chat;
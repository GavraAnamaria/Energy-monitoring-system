import React from 'react'

import APIResponseErrorMessage from "../../commons/errorhandling/api-response-error-message";
import {
    Button,
    Card,
    CardHeader,
    Col,
    Modal,
    ModalBody,
    ModalHeader,
    Row
} from 'reactstrap';
import * as API_USERS from "./client-api"
import PersonTable from "../components/person-table";
import ConsumptionForm from "./consumption-form";
import mes from "../../commons/images/msg.png";
import Chat from "../admin/Chat";


const buttonStyle={
    position: 'absolute',
    left: '50%',
    backgroundColor:'lightBlue',
    border: 'none',
    color: 'white',
    padding: '15px 32px',
    textAlign: 'center',
    textDecoration: 'none',
    display: 'inline-block',
    fontSize: '16px',
}


class Client extends React.Component {

    constructor(props) {
        super(props);
        //this.reload = this.reload.bind(this);
        this.state = {
            form:false,
            chate:false,
            selected: false,
            collapseForm: false,
            tableData: [],
            isLoaded: false,
            errorStatus: 0,
            error: null
        };
    }

    componentDidMount() {
        this.fetchData2();
    }

    toggleForm(){
        this.setState({
            form: !this.state.form,
        })
    }
    toggleChat(){
        this.setState({
            chat: !this.state.chat,
        })
    }

    // fetchData() {
    //     let id;
    //     API_USERS.getData(0,(result, status, err)  => {
    //             if (result !== null && status === 200) {
    //                 for (let i = 0; i < result.length; i++)
    //                     if (result[i].name === localStorage.getItem("userName")) {
    //                         id = result[i].id;
    //                         break;
    //                     }
    //             }else {
    //                 this.setState({
    //                     errorStatus: status,
    //                     error: err
    //                 });}});
    //     API_USERS.getData(1,(result, status, err)  => {
    //             if (result !== null && status === 200) {
    //                 let d=[];
    //                 for(let i=0; i<result.length;i++)
    //                     if(result[i].user_id===id)
    //                         d.push(result[i]);
    //                 this.setState({
    //                     isLoaded: true,
    //                     tableData:d,
    //                 })
    //             } else {
    //                 this.setState(({
    //                     errorStatus: status,
    //                     error: err
    //                 }));
    //             }
    //         }
    //     );
    // }
  fetchData2() {
        API_USERS.getDevicesByUser( this.props.user,(result, status, err)  => {
                if (result !== null && status === 200) {
                    this.setState({
                        isLoaded: true,
                        tableData:result,
                    })
                } else {
                    this.setState(({
                        errorStatus: status,
                        error: err
                    }));
                }
            }
        );
    }



    render() {
        return (
            <div>
                <CardHeader>
                    <strong> {this.props.user}'s Devices: </strong>
                    <Button style={{float:'right'}}  onClick={() =>this.toggleChat()} > <img src={mes} width={"25"} height={"25"}/></Button>
                </CardHeader>
                <Card>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <PersonTable tableData = {this.state.tableData} index={1}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                            <br/>
                            <br/>
                        </Col>
                    </Row>
                </Card>
                <Button style={buttonStyle} onClick={() =>this.toggleForm()}>Daily consumption</Button>
                <Modal isOpen={(this.state.chat)} toggle={() =>this.toggleChat()} size="lg">
                    <ModalHeader toggle={() =>this.toggleChat()}> MESSAGES: </ModalHeader>
                    <ModalBody>
                        <Chat user={this.props.user} />
                    </ModalBody>
                </Modal>
                <Modal isOpen={this.state.form} toggle={() =>this.toggleForm()} size="lg">
                    <ModalHeader toggle={() =>this.toggleForm()}> Daily consumption: </ModalHeader>
                    <ModalBody>
                        <ConsumptionForm data={this.state.tableData}/>
                    </ModalBody>
                </Modal>
            </div>
        )

    }
}


export default Client;

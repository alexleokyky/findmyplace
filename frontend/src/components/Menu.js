import React, { Component } from 'react';
import { Navbar, NavItem, Button } from 'react-materialize';
import Logout from './Logout';
import '../styles/Menu.css';

class Menu extends Component {

    constructor(options) {
        super(options);

        this.state = {
            isLoggedIn: true
        };
    }

    render() {
        const isLoggedIn = this.state.isLoggedIn;

        return (
            <div className="menu-container">
                <div className="menu-wrapper">
                    <Navbar brand="logo" className="menu" right>
                        <NavItem href='#/'>Home</NavItem>
                        <NavItem href='#/map'>Map</NavItem>
                        <NavItem href='#about'>About us</NavItem>
                        <Logout isLoggedIn={this.state.isLoggedIn} logout={this.onLogoutHandler.bind(this)}/>
                        <NavItem href='#/sign-up' id="auth-sign-up" className={isLoggedIn ? "hidden" : ""}>
                            <Button waves="light">
                                Sign Up
                            </Button>
                        </NavItem>

                        <NavItem href='#/sign-in' id="auth-sign-in" className={isLoggedIn ? "hidden" : ""}>
                            Sign In
                        </NavItem>
                    </Navbar>
                </div>
            </div>
        );
    }

    onLogoutHandler() {
        this.setState({
            isLoggedIn: false
        });
    }

}

export default Menu;
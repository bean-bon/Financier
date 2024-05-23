//
//  TransactionList.swift
//  iosAppUITests
//
//  Created by Benjamin Groom on 23/05/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import XCTest
import shared

final class TransactionListTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        
        // In UI tests it is usually best to stop immediately when a failure occurs.
        continueAfterFailure = false

        // In UI tests it’s important to set the initial state - such as interface orientation - required for your tests before they run. The setUp method is a good place to do this.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testClickingNewButtonNavigatesToNewScreen() {
        let app = XCUIApplication()
        app.launch()
        app.buttons[ListView.Accessibility.addTransaction].tap()
        XCTAssert(app.navigationBars.staticTexts[TextKeys.Title.shared.newTransaction.get()].exists)
    }

}

// RadioButtonEx.cpp : implementation file
//

#include "stdafx.h"
#include "GunManagerDemo.h"
#include "RadioButtonEx.h"


// CRadioButtonEx

IMPLEMENT_DYNAMIC(CRadioButtonEx, CButton)

CRadioButtonEx::CRadioButtonEx()
{

}

CRadioButtonEx::~CRadioButtonEx()
{
}


BEGIN_MESSAGE_MAP(CRadioButtonEx, CButton)
END_MESSAGE_MAP()



// CRadioButtonEx message handlers
void CRadioButtonEx::DrawItem(LPDRAWITEMSTRUCT lpDrawItemStruct)
{
	CDC* pDC = CDC::FromHandle(lpDrawItemStruct->hDC);
	pDC->SetTextColor(RGB(255, 255, 255));
}

